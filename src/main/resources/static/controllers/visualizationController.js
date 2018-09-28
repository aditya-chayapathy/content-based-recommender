'use strict';

app.controller('VisualizationController', function ($scope, $location, $rootScope, $http) {
    if ($rootScope.userId === "" || $rootScope.username === "") {
        $location.path("/");
    }

    $scope.userId = $rootScope.userId;

    $scope.username = $rootScope.username;

    $http({
        method: 'GET',
        url: '/visualization/getKeyPressStats?userId=' + $scope.userId
    }).then(function (response) {
        if (Object.keys(response.data).length === 0) {
            $scope.graph1Error = true;
        } else {
            var data = [{
                x: Object.keys(response.data),
                y: Object.values(response.data),
                type: 'bar'
            }];
            var layout = {
                autosize: false,
                width: 500,
                height: 500,
                margin: {
                    l: 50,
                    r: 50,
                    b: 50,
                    t: 50,
                    pad: 4
                }
            };
            Plotly.newPlot('graph1', data, layout);
        }
    });

    $http({
        method: 'GET',
        url: '/visualization/getTimeSpentInAppStats?userId=' + $scope.userId
    }).then(function (response) {
        if (response.data.length === 0) {
            $scope.graph2Error = true;
        } else {
            var data = [{
                y: response.data,
                boxpoints: 'all',
                jitter: 0.3,
                pointpos: -1.8,
                type: 'box'
            }];
            var layout = {
                autosize: false,
                width: 500,
                height: 500,
                margin: {
                    l: 50,
                    r: 50,
                    b: 50,
                    t: 50,
                    pad: 4
                }
            };
            Plotly.newPlot('graph2', data, layout);
        }
    });

    $http({
        method: 'GET',
        url: '/visualization/getMouseTrajectoryStats?userId=' + $scope.userId
    }).then(function (response) {
        if (response.data.x.length <= 4) {
            $scope.graph3Error = true;
        } else {
            var data = [{
                x: response.data.x,
                y: response.data.y,
                type: 'histogram2d'
            }];
            var layout = {
                autosize: false,
                width: 500,
                height: 500,
                margin: {
                    l: 50,
                    r: 50,
                    b: 50,
                    t: 50,
                    pad: 4
                }
            };
            Plotly.newPlot('graph3', data, layout);
        }
    });

    $http({
        method: 'GET',
        url: '/visualization/getActionCounts?userId=' + $scope.userId
    }).then(function (response) {
        var error = true;
        for (var i in response.data) {
            if (response.data[i] !== 0) {
                error = false;
                break;
            }
        }

        if (error) {
            $scope.graph4Error = true;
        } else {
            var data = [{
                values: Object.values(response.data),
                labels: Object.keys(response.data),
                type: 'pie'
            }];
            var layout = {
                autosize: false,
                width: 500,
                height: 350,
                margin: {
                    l: 50,
                    r: 50,
                    b: 50,
                    t: 50,
                    pad: 4
                }
            };
            Plotly.newPlot('graph4', data, layout);
        }
    });

    $http({
        method: 'GET',
        url: '/visualization/getHighlightedWordsStats?userId=' + $scope.userId
    }).then(function (response) {
        if (Object.keys(response.data).length === 0) {
            $scope.graph5Error = true;
        } else {
            var data = [{
                x: Object.keys(response.data),
                y: Object.values(response.data),
                type: 'bar'
            }];
            var layout = {
                autosize: false,
                width: 500,
                height: 500,
                margin: {
                    l: 50,
                    r: 50,
                    b: 50,
                    t: 50,
                    pad: 4
                }
            };
            Plotly.newPlot('graph5', data, layout);
        }
    });

    $scope.accountTab = function () {
        $location.path("/account")
    };

});