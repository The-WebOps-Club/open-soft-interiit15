'use strict';

/**
 * @ngdoc function
 * @name villagerHelpApp.controller:DemandCtrl
 * @description
 * # DemandCtrl
 * Controller of the villagerHelpApp
 */
angular.module('villagerHelpApp')
  .controller('DemandCtrl', function ($scope, $http) {
    $scope.awesomeThings = [
      'HTML5 Boilerplate',
      'AngularJS',
      'Karma'
    ];
    $scope.keyword = '';
    $scope.fetch = function(){
    	
    	$http.get('http://localhost:8000/fetch/?keyword='+$scope.keyword+'&format=json').success(function(data){
    		console.log(data);
    		$scope.data = data;
    		localforage.getItem('topics', function(err, topics){
    			if(err){
    				console.log('err',err)
    			}
    			else{
    				console.log('topics',topics)
    				if(topics){
    					topics.push(data)
    				}
    				else{
    					topics = [data];
    				}
    				localforage.setItem('topics', topics).then(function(value) {
					    console.log(value + ' was set!');
					}, function(error) {
					    console.error(error);
					});
    			}
    		})
    	}).error(function(error){
    		console.log(error);
    	});
    }
  });
