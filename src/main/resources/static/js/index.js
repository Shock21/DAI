var app = angular.module('app', ['ngSanitize', 'MassAutoComplete']);
app.controller('mainCtrl', function ($scope, $http, $sce, $q) {
    $scope.dirty = {};

    function suggest_state(term) {
        var results = [];

        $http
            .post(
                '/search',
                {'contains': term.toLowerCase().trim()}
            )
            .then(function (response) {

            });

    }

    $scope.autocomplete_options = {
        suggest: suggest_state
    };
});