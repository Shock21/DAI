var app = angular.module('app', ['ngSanitize', 'MassAutoComplete', 'ngAudio']);
app.controller('mainCtrl', function ($scope, $http, $sce, $q, ngAudio) {
    $scope.dirty = {};
    $scope.tags = [];
    $scope.results = [];
    $scope.songLyrics = {};
    $scope.active = true;

    function add_tag(selected) {
        //$scope.tags.push(selected);
        $scope.dirty = {};

        var ap = new APlayer({
            element: document.getElementById('player'),                       // Optional, player element
            theme: '#e6d0b2',                                                  // Optional, theme color, default: #b7daff
            music: selected.music_data
        });
    }

    $scope.getLyrics = function() {
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

                if(response.data.artistSearch) {
                    var music = [];
                    angular.forEach(response.data.songInfo, function (song) {
                        music.push({title : song.songTitle, author : song.artistName , url : 'http://localhost:8080/audio?songName=' + song.songTitle  + '&artistName=' + song.artistName});
                    });
                    results.push({label : $sce.trustAsHtml(response.data.artistName), value : response.data.artistName, music_data: music});
                } else {
                    angular.forEach(response.data.songInfo, function (song) {
                        var music = [{
                            title: song.songTitle,
                            author: song.artistName,
                            url: 'http://localhost:8080/audio?songName=' + song.songTitle + '&artistName=' + song.artistName
                        }];
                        results.push({label: $sce.trustAsHtml(song.name), value: song.songTitle, music_data: music})
                    });
                }

                return results;
            });

    }

    $scope.autocomplete_options = {
        suggest: suggest_state,
        on_select: add_tag
    };
});



