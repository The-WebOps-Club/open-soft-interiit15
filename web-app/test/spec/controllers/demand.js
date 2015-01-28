'use strict';

describe('Controller: DemandCtrl', function () {

  // load the controller's module
  beforeEach(module('villagerHelpApp'));

  var DemandCtrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    DemandCtrl = $controller('DemandCtrl', {
      $scope: scope
    });
  }));

  it('should attach a list of awesomeThings to the scope', function () {
    expect(scope.awesomeThings.length).toBe(3);
  });
});
