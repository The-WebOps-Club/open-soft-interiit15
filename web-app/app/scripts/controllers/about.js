'use strict';

/**
 * @ngdoc function
 * @name villagerHelpApp.controller:AboutCtrl
 * @description
 * # AboutCtrl
 * Controller of the villagerHelpApp
 */
angular.module('villagerHelpApp')
  .controller('AboutCtrl', function ($scope) {
    $scope.awesomeThings = [
      'HTML5 Boilerplate',
      'AngularJS',
      'Karma'
    ];
  });
