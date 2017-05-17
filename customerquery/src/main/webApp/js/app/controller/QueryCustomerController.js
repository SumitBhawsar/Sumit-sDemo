var app = angular.module("CustomerQuery");

app.controller('QueryCustomerController', [ '$scope', '$http', '$mdDialog', function($scope, $http, $mdDialog) {
	$scope.customer = {};
	$scope.customer.firstName = 'sumit';
	$scope.customer.lastName = 'bhawsar';
	$scope.customer.mobileno = '7028026022';
	
	$scope.customers = [];
	
	$scope.getCustomers = function(){
		var data = JSON.stringify($scope.customer);
		$http.post('/getCustomers', data).success(function(data, status, headers, config) {
			$scope.customers = data;
		}).error(function(data, status, headers, config) {
			$scope.openFromLeft('Failed', data.message, data.status + ' : ' + data.error, 'Ohhh !');
		})
	}
	
	$scope.openFromLeft = function(title, message, ariaLabel, okButtonText) {
		$mdDialog.show(
			$mdDialog.alert()
				.clickOutsideToClose(true)
				.title(title)
				.textContent(message)
				.ariaLabel(ariaLabel)
				.ok(okButtonText)
				// You can specify either sting with query selector
				.openFrom('#left')
				// or an element
				.closeTo(angular.element(document.querySelector('#right')))
		);
	};
	
	
	$scope.clearData = function(){
		$scope.customer = {};
		$scope.customer.firstName = '';
		$scope.customer.lastName = '';
		$scope.customer.mobileno = '';
	}
} ]);