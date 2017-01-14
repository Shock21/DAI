var app = angular.module('app', ['ngSanitize', 'MassAutoComplete']);
app.controller('mainCtrl', function ($scope, $http, $sce, $q) {
    $scope.dirty = {};
    $scope.tags = [];

    function add_tag(selected) {
        $scope.tags.push(selected);
        $scope.dirty = {};
    }

    function convert_duration(duration) {
        return moment().startOf('day')
            .seconds(duration)
            .format('mm:ss');
    }

    function suggest_state(term) {
        $scope.dirty.term = term;
        $scope.tags = [];
        return $http.post('/search',{'contains': term.toLowerCase().trim()}            )
            .then(function (response) {
                var results = [];

                var number = 0;
                angular.forEach(response.data.songInfo, function(song) {
                    number++;
                    results.push({label : $sce.trustAsHtml(song.name), value : song.name, number: number,
                        artist: song.artist, duration: convert_duration(song.duration)});
                });

                return results;
            });

    }

    $scope.autocomplete_options = {
        suggest: suggest_state,
        on_select: add_tag
    };
});