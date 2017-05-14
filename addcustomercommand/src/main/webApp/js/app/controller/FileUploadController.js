var app = angular.module("ApiMimic");

/*app.controller('FileUploadController', FileUploadController('$scope', '$rootScope', 'FileUploadServices'));

function FileUploadController($scope, $rootScope, fileUploadServices) {
    $scope.files = [];
    $scope.percentage = 0;
    $scope.upload = function () {
    	fileUploadServices.upload();
        $scope.files = [];
    };
    $rootScope.$on('fileAdded', function (e, call) {
        $scope.files.push(call);
        $scope.$apply();
    });
    $rootScope.$on('uploadProgress', function (e, call) {
        $scope.percentage = call;
        $scope.$apply();
    });
}*/

app.controller('FileUploadController', [ '$scope', '$rootScope', 'FileUploadService', '$mdDialog', function($scope, $rootScope, fileUploadService, $mdDialog) {
	$scope.test = 'abc';
	$scope.files = [];
	$scope.percentage = 0;
	$scope.upload = function() {
		fileUploadService.upload();
		$scope.openFromLeft('Success', 'Files Successfully Uploaded!', 'unknown', 'Great !');
		$scope.files = [];
	};
	$rootScope.$on('fileAdded', function(e, call) {
		$scope.files.push(call);
		$scope.$apply();
	});
	$rootScope.$on('uploadProgress', function(e, call) {
		$scope.percentage = call;
		$scope.$apply();
	});


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
} ]);