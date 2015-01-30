'use strict';
var content
var parsedContent
var parseContent = function parseContent (pcontent) {
	pcontent = pcontent.split('\n').join('<br>');
	var h3regex = /=== (\w*\s*\-*\w*)* ===/gmi
	pcontent = pcontent.replace(h3regex, '<h4>$1</h4>')	
  pcontent = pcontent.split('\n').join('<br>');
	var h2regex = /== (\w*\s*\-*\w*)* ==/gmi
	pcontent = pcontent.replace(h2regex, '<h2>$1</h2>')
	
	return pcontent
}

/**
 * @ngdoc function
 * @name villagerHelpApp.controller:ViewCtrl
 * @description
 * # ViewCtrl
 * Controller of the villagerHelpApp
 */
angular.module('villagerHelpApp')
  .controller('ViewCtrl', function ($scope, $routeParams) {
    $scope.awesomeThings = [
      'HTML5 Boilerplate',
      'AngularJS',
      'Karma'
    ];

    var id = $routeParams.id;
    $scope.topic = {};
     localforage.getItem('topics').then(function(topics){
    	console.log(topics)
    	for (var i = topics.length - 1; i >= 0; i--) {
    		if(topics[i].page_id == id){
    			$scope.topic = topics[i];
    			$scope.topic.content = parseContent($scope.topic.content);
    		}
    	};
    	$scope.$apply();
    })

  });

