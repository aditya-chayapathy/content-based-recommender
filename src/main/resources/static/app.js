'use strict';

var app = angular.module('userBehaviorLogging', ['ngRoute']);
app.config(function ($routeProvider) {
    $routeProvider
        .when('/index.html', {
            controller: 'LoginController',
            templateUrl: 'views/login.html'
        })
        .when('/', {
            controller: 'LoginController',
            templateUrl: 'views/login.html'
        })
        .when('/login', {
            controller: 'LoginController',
            templateUrl: 'views/login.html'
        })
        .when('/account', {
            controller: 'AccountController',
            templateUrl: 'views/account.html'
        })
        .when('/visualization', {
            controller: 'VisualizationController',
            templateUrl: 'views/visualization.html'
        })
        .when('/stackoverflow', {
            controller: 'StackOverFlowController',
            templateUrl: 'views/stackoverflow.html'
        })
        .when('/error', {
            templateUrl: 'views/error.html'
        })
        .otherwise({redirectTo: '/error'});
});
app.run(function ($rootScope) {
    $rootScope.username = "";
    $rootScope.userId = "";
});