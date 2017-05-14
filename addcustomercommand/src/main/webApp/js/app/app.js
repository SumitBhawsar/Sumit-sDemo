var app = angular.module("Onboarding", [ 'ngMaterial' ]);

app.config(function($mdThemingProvider) {
	  $mdThemingProvider.theme('docs-dark')
	    .primaryPalette('blue')
	    .accentPalette('cyan');
	});