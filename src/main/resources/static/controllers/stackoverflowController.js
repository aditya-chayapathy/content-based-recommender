'use strict';

app.controller('StackOverFlowController', function ($scope, $location, $rootScope, $http) {
    if ($rootScope.userId === "" || $rootScope.username === "") {
        $location.path("/");
    }

    $scope.userId = $rootScope.userId;

    $scope.username = $rootScope.username;

    $scope.accountTab = function () {
        $location.path("/account");
    };

    $scope.keyPress = function ($event) {
        $http({
            method: 'POST',
            url: '/event/addEvent?userId=' + $scope.userId + '&eventType=KEY_PRESS&eventInfo=' + $event.key
        }).then(function (response) {
            if (!response) {
                console.log("ERROR OCCURRED");
            }
        });
    };

    $scope.mouseMove = function ($event) {
        var eventInfo = $event.x + ',' + $event.y;
        $http({
            method: 'POST',
            url: '/event/addEvent?userId=' + $scope.userId + '&eventType=MOUSE_MOVE&eventInfo=' + eventInfo
        }).then(function (response) {
            if (!response) {
                console.log("ERROR OCCURRED");
            }
        });
    };

    $scope.mouseClick = function ($event) {
        var eventInfo = $event.x + ',' + $event.y;
        $http({
            method: 'POST',
            url: '/event/addEvent?userId=' + $scope.userId + '&eventType=MOUSE_CLICK&eventInfo=' + eventInfo
        }).then(function (response) {
            if (!response) {
                console.log("ERROR OCCURRED");
            }
        });
    };

    $scope.mouseDoubleClick = function ($event) {
        var eventInfo = $event.x + ',' + $event.y;
        $http({
            method: 'POST',
            url: '/event/addEvent?userId=' + $scope.userId + '&eventType=MOUSE_DOUBLE_CLICK&eventInfo=' + eventInfo
        }).then(function (response) {
            if (!response) {
                console.log("ERROR OCCURRED");
            }
        });
    };

    $scope.copy = function ($event) {
        $http({
            method: 'POST',
            url: '/event/addEvent?userId=' + $scope.userId + '&eventType=COPY&eventInfo=' + $event.target.data
        }).then(function (response) {
            if (!response) {
                console.log("ERROR OCCURRED");
            }
        });
    };

    $scope.paste = function ($event) {
        $http({
            method: 'POST',
            url: '/event/addEvent?userId=' + $scope.userId + '&eventType=PASTE&eventInfo=NONE'
        }).then(function (response) {
            if (!response) {
                console.log("ERROR OCCURRED");
            }
        });
    };

});