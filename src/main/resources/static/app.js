'use strict';

var app = angular.module('ContentBasedRecommendation', ['ngRoute']);
app.config(function ($routeProvider) {
    $routeProvider
        .when('/index', {
            controller: 'StartController',
            templateUrl: 'views/start.html'
        })
        .otherwise({redirectTo: '/index'});
});