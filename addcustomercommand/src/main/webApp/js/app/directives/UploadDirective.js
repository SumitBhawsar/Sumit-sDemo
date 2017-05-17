var app = angular.module("ApiMimic");


app.directive('upload', ['FileUploadService', function(fileUploadService) {
	return {
		restrict : 'A',
		link : function(scope, element, attrs) {
			$(element).fileupload({
				dataType : 'text',
				add : function(e, data) {
					fileUploadService.add(data);
				},
				progressall : function(e, data) {
					var progress = parseInt(data.loaded / data.total * 100, 10);
					fileUploadService.setProgress(progress);
				},
				done : function(e, data) {
					fileUploadService.setProgress(0);
				}
			});
		}
	};
}]);	