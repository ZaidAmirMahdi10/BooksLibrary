/**
 * @author Zaid Mahdi
 * @author Seva Meyer
 *
 * A Book controller
 * responsible for adding, removing,
 * editing and viewing books
 */
(function () {
    'use strict';

var app = angular.module("simpleApp");


app.controller('bookController',['$scope', '$location','$modal', '$http','$rootScope','$route','$routeParams',
    function ($scope, $location,$modal, $http, $rootScope,$route,$routeParams){

        $scope.collection = []; //a list of books displayed
        $scope.total_list = []; // the whole list from DB
        $scope.totalItems = 0; //items in the list of books
        $scope.limit = 10 ; //books per page
        $scope.currentPage = 1; //displayed page
        $scope.book = { //a book object
            "id": "",
            "title": "",
            "author": "",
            "date": "",
            "description": "",
            "image": ""
        };


        $scope.sortProperty = 'name';
        $scope.reverseSort = false;


        /*===========SORT BOOKS============*/
         $scope.sort = function (prop) {
            $scope.sortProperty = prop;
            $scope.reverseSort = !this.reverseSort;
         };

        /*===========LIST BOOKS FROM DB AND SEARCH============*/
        $scope.listBooks = function(){
            $http.get("/api/books",{
                params: {
                    search: $scope.search
                }
            })
                .success(function(data, status, headers, config){
                    $scope.total_list = data;
                    $scope.sliceList();

                });
        };

        /*===========MANAGE PAGINATION============*/
        $scope.sliceList = function(){
            $scope.totalItems = $scope.total_list.length;
            $scope.collection = $scope.total_list.slice(($scope.currentPage-1)*$scope.limit,($scope.currentPage)*$scope.limit );
            $scope.numOfPages = Math.ceil($scope.totalItems/$scope.limit);
        };


        /*===========TURN THE PAGE OR CHANGE LIMIT============*/
        $scope.pageChanged = function(){
            $scope.sliceList();
        };

        /*===========OPEN BOOK INFO POPUP============*/
        $scope.openInfo = function (obj) {
            $scope.book = obj;



            var modalInstance = $modal.open({
                templateUrl: 'partials/bookinfo.html',
                controller: 'bookInfoController',
                size: 'lg',
                resolve: {
                    book: function () {
                        $scope.getEntry();
                        return $scope.book;
                    }
                }
            });

            modalInstance.result.then(function () {

            }, function () {
                console.log('Modal dismissed at: ' + new Date());
            });
        };

        /*===========OPEN NEW BOOK POPUP============*/
        $scope.openNew = function () {

            var modalInstance = $modal.open({
                templateUrl: 'partials/addbook.html',
                controller: 'newBookController',
                windowClass:'add-dialog',
                size: 'lg',
                resolve: {
                    book: function(){
                        return $scope.book;
                    }

                }
            });

            modalInstance.result.then(function () {

            }, function () {
                console.log('Modal dismissed at: ' + new Date());
            });
        };


        /*===========GET ONE BOOK FROM DB BY ID============*/
        $scope.getEntry = function() {

            $http.get('/api/books/' + $scope.book.id).success(
                function(data) {
                    $scope.book = data;
                });

        };


        /*===========REMOVE A BOOK FROM LIST AND DB============*/
        $scope.removeEntry = function (index) {
            $http.delete('/api/books/' + index.id)
                .success(function(data, status, headers, config){
                    var item = $scope.collection.indexOf(index.id);
                    $scope.collection.splice(item, 1);
                    $route.reload();
                });
        };
    }]);

}());