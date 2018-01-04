var app = angular.module('app', ['ngSanitize', 'MassAutoComplete', 'ngAudio', 'angularSoundManager', 'NumberConvertService']);
app.config([
    '$interpolateProvider', function($interpolateProvider) {
        return $interpolateProvider.startSymbol('{(').endSymbol(')}');
    }
])
app.controller('mainCtrl', ['$scope', '$http', '$sce', 'numberConvert', 'angularPlayer', function ($scope, $http, $sce, numberConvert, angularPlayer) {
    $scope.dirty = {};
    $scope.tags = [];
    $scope.results = [];
    $scope.songLyrics = {};
    $scope.songs = [];
    $scope.playlist = [];
    $scope.active = true;

    // function add_tag(selected) {
    //     //$scope.tags.push(selected);
    //     $scope.dirty = {};
    //
    //     $scope.songs = selected.music_data;
    //
    //     // var ap = new APlayer({
    //     //     element: document.getElementById('player'),                       // Optional, player element
    //     //     theme: '#e6d0b2',                                                  // Optional, theme color, default: #b7daff
    //     //     music: selected.music_data
    //     // });
    // }

    $scope.getLyrics = function() {
        angularPlayer.currentTrackData();
        $http.post('/lyrics')
            .then(function (response) {
                $scope.songLyrics = response.data;
            });
    }

    function convert_duration(duration) {
        return moment().startOf('day')
            .seconds(duration)
            .format('mm:ss');
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
                        music.push({id: numberConvert.convertNumber(id++), title : song.songTitle, artist : song.artistName , url : 'http://localhost:8080/play?songName=' + song.songTitle  + '&artistName=' + song.artistName});
                    });
                    results.push({label : $sce.trustAsHtml(response.data.artistName), value : response.data.artistName, music_data: music});
                } else {
                    angular.forEach(response.data.songInfo, function (song) {
                        music.push({id: numberConvert.convertNumber(id++), title : song.songTitle, artist : song.artistName , url : 'http://localhost:8080/play?songName=' + song.songTitle  + '&artistName=' + song.artistName});
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
}]);



