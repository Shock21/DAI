var app = angular.module('app', ['ngSanitize', 'MassAutoComplete', 'ngAudio', 'angularSoundManager', 'NumberConvertService', 'ui.bootstrap']);
app.config([
    '$interpolateProvider', function($interpolateProvider) {
        return $interpolateProvider.startSymbol('{(').endSymbol(')}');
    }
])
app.directive('ngFiles', ['$parse', function ($parse) {

    function fn_link(scope, element, attrs) {
        var onChange = $parse(attrs.ngFiles);
        element.on('change', function (event) {
            onChange(scope, { $file: event.target.files });
        });
    };

    return {
        link: fn_link
    }
} ])
app.controller('mainCtrl', ['$scope', '$http', '$sce', '$uibModal', 'numberConvert', 'angularPlayer', function ($scope, $http, $sce, $uibModal, numberConvert, angularPlayer) {
    $scope.dirty = {};
    $scope.tags = [];
    $scope.results = [];
    $scope.songLyrics = {};
    $scope.songs = [];
    $scope.playlist = [];
    $scope.active = true;

    $scope.getLyrics = function() {
        angularPlayer.currentTrackData();
        $http.get('/lyrics?songTitle=' + angularPlayer.currentTrackData().title + '&artistName=' + angularPlayer.currentTrackData().artist)
            .then(function (response) {
                $scope.songLyrics = response.data.lyrics;
            });
    }

    function suggest_state(term) {
        $scope.dirty.term = term;
        $scope.tags = [];
        return $http.post('/search',{'contains': term.toLowerCase().trim()})
            .then(function (response) {
                var results = [];
                var id = 1;
                var music = [];

                if(response.data.artistSearch) {
                    angular.forEach(response.data.songInfo, function (song) {
                        music.push({id: numberConvert.convertNumber(id++), title : song.songTitle, artist : song.artistName , url : 'http://localhost:8080/play?songName=' + song.songTitle});
                    });
                    results.push({label : $sce.trustAsHtml(response.data.artistName), value : response.data.artistName, music_data: music});
                } else {
                    angular.forEach(response.data.songInfo, function (song) {
                        music.push({id: numberConvert.convertNumber(id++), title : song.songTitle, artist : song.artistName , url : 'http://localhost:8080/play?songName=' + song.songTitle});
                        results.push({label: $sce.trustAsHtml(song.songTitle), value: song.songTitle, music_data: music})
                    });
                }

                return results;
            });

    }

    $scope.ac_options_users = {
        suggest: suggest_state,
        on_select: function (selected) {
            $scope.dirty = {};
            $scope.songs = selected.music_data;
        }
    };

    $scope.song = {
        name: '',
        artist: '',
    };

    $scope.songFile = null;

    $scope.open = function () {
        $uibModal.open({
            templateUrl: 'modal-form.html', // loads the template
            backdrop: true, // setting backdrop allows us to close the modal window on clicking outside the modal window
            windowClass: 'modal', // windowClass - additional CSS class(es) to be added to a modal window template
            controller: function ($scope, $uibModalInstance) {
                $scope.getTheFiles = function ($file) {
                    $scope.songFile = $file;
                };

                $scope.submit = function () {
                    var fd = new FormData();
                    fd.append('file', $scope.songFile[0]);
                    fd.append('song', new Blob([JSON.stringify({'songName': $scope.song.name, 'artistName': $scope.song.artist})], {
                        type: "application/json"
                    }));
                    $http.post('/upload', fd, {
                        headers: {'Content-Type': undefined}
                    }).then(function (response) {
                        $uibModalInstance.dismiss('cancel');
                    }, function (response) {
                        $uibModalInstance.dismiss('cancel');
                    });
                }
                $scope.cancel = function () {
                    $uibModalInstance.dismiss('cancel');
                };
            },
            resolve: {
                user: function () {
                    return $scope.user;
                }
            }
        });//end of modal.open
    };

}]);

