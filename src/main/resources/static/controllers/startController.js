'use strict';

app.controller('StartController', function ($scope, $http) {
    $http({
        method: 'GET',
        url: '/webCrawler/dataForUIRendering'
    }).then(function (response) {
        $scope.data = response.data
    });
});