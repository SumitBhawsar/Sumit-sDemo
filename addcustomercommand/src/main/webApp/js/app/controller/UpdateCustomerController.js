var app = angular.module("Onboarding");

app.controller('UpdateCustomerController', [ '$scope', '$http', '$mdDialog', function($scope, $http, $mdDialog) {
	$scope.customer = {};
	$scope.customer.customerId = '';
	$scope.customer.firstName = '';
	$scope.customer.lastName = '';
	$scope.customer.mobileno = '';
	$scope.customer.address = {};
	$scope.customer.address.addressLine1 = '';
	$scope.customer.address.addressLine2 = '';
	$scope.customer.address.addressLine3 = '';
	$scope.customer.address.postCode = '';
	$scope.customer.address.city = '';
	$scope.customer.address.country = '';
	
	$scope.updateCustomer = function(){
		var data = JSON.stringify($scope.customer);
		$http.post('/updateCustomer', data).success(function(data, status, headers, config) {
			$scope.openFromLeft('Success', 'Customer Successfully updated !', 'unknown', 'Great !');
			$scope.clearData();
		}).error(function(data, status, headers, config) {
			$scope.openFromLeft('Failed', 'Updating Customer is failed !', 'unknown', 'Ohhh !');
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
		$scope.customer.customerId = '';
		$scope.customer.firstName = '';
		$scope.customer.lastName = '';
		$scope.customer.mobileno = '';
		$scope.customer.address = {};
		$scope.customer.address.addressLine1 = '';
		$scope.customer.address.addressLine2 = '';
		$scope.customer.address.addressLine3 = '';
		$scope.customer.address.postCode = '';
		$scope.customer.address.city = '';
		$scope.customer.address.country = '';
	}
} ]);