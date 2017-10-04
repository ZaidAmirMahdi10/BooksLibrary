/**
 * Created by Seva on 27/05/2015.
 */
(function() {
	'use strict';

	var app = angular.module("simpleApp");

    /*==========SHOW BOOK INFO==================*/
	app.controller('bookInfoController', [
			'$scope',
			'$modalInstance',
			'$http',
             'book',
			function($scope, $modalInstance, $http, book) {

				$scope.book = book;
                $scope.save = function(){
                    $scope.book.date = Date.parse($scope.book.date);
                    $http.put('/api/books/' + $scope.book.id, $scope.book)
                        .success(function(data, status, headers, config){
                        });
                };

				$scope.cancel = function() {
					$modalInstance.dismiss('cancel');
				};

			} ]);


    /*==========SHOW ADD BOOK POPUP==================*/
    app.controller('newBookController', [
        '$scope',
        '$modalInstance',
        '$http',
        '$route',
        'book',
        function($scope, $modalInstance, $http, $route,book) {

             $scope.book = book;


             $scope.add = function() {

                 $scope.book.title = $scope.newData.title;
                 $scope.book.author = $scope.newData.author;
                 $scope.book.date = Date.parse($scope.newData.date);
                 $scope.book.description = $scope.newData.description;
                 $scope.book.image = $scope.newData.image;

               
                 $http.post("/api/books", $scope.book)
                     .success(function(data, status, headers, config)
                     {
                         alert("The book is added");

                         $route.reload();

                     });
             $modalInstance.close();
             };

             $scope.cancel = function() {
             $modalInstance.dismiss('cancel');
             };



        } ]);

}());