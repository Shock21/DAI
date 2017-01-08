var app = angular.module('app', ['ngSanitize', 'MassAutoComplete']);
app.controller('mainCtrl', function ($scope, $http, $sce, $q) {
    $scope.dirty = {};
    $scope.tags = [];

    function add_tag(selected) {
        $scope.tags.push(selected.value);
        $scope.dirty = {};
    }

    function suggest_state(term) {
        $scope.dirty.term = term;
        return $http.post('/search',{'contains': term.toLowerCase().trim()}            )
            .then(function (response) {
                var results = [];

                angular.forEach(response.data.songNames, function(song) {
                    results.push({label : $sce.trustAsHtml(song), value : song});
                });

                return results;
            });

    }

    $scope.autocomplete_options = {
        suggest: suggest_state,
        on_select: add_tag
    };
});