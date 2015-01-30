'use strict';

/**
 * @ngdoc function
 * @name villagerHelpApp.controller:BrowseCtrl
 * @description
 * # BrowseCtrl
 * Controller of the villagerHelpApp
 */
angular.module('villagerHelpApp')
  .controller('BrowseCtrl', function ($scope) {
    $scope.awesomeThings = [
      'HTML5 Boilerplate',
      'AngularJS',
      'Karma'
    ];
    $scope.topics = [];
    localforage.getItem('topics').then(function(topics){
    	console.log(topics)
    	$scope.topics = topics;
    	$scope.$apply();
    })
  });
