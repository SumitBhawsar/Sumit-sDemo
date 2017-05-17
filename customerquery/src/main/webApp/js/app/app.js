var app = angular.module("CustomerQuery", [ 'ngMaterial' ]);

app.config(function($mdThemingProvider) {
	  $mdThemingProvider.theme('docs-dark')
	    .primaryPalette('blue')
	    .accentPalette('cyan');
	});