'use strict';

app.controller('AccountController', function ($scope, $location, $rootScope, $http) {
    if ($rootScope.userId === "" || $rootScope.username === "") {
        $location.path("/");
    }

    $scope.userId = $rootScope.userId;

    $scope.username = $rootScope.username;

    $http({
        method: 'GET',
        url: '/login/getLoginInfoForUser?userId=' + $scope.userId + '&loginType=LOG_IN'
    }).then(function (response) {
        $scope.lastLoginDetails = response.data
    });

    $scope.logout = function () {
        $http({
            method: 'POST',
            url: '/user/logUserOut?userId=' + $scope.userId
        }).then(function (response) {
            if (response) {
                $rootScope.userId = "";
                $rootScope.username = "";
                $location.path("/");
            }
        });
    };

    $scope.socialVisualizationTab = function () {
        $location.path("/visualization")
    };

    $scope.stackOverFlowTab = function () {
        $location.path("/stackoverflow")
    }

});