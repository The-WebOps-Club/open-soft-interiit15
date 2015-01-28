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
  });
