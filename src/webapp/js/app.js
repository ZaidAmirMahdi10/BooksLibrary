/**
 * Created by Seva on 27/04/2015.
 */


(function () {
    'use strict';
    var app = angular.module('simpleApp', ['ngRoute', 'ui.bootstrap']);


    app.config(['$routeProvider',
        function ($routeProvider) {
            $routeProvider
                .when('/', {
                    templateUrl: 'partials/listbooks.html',
                    controller:'bookController'
                })
                .when('/books', {
                	templateUrl: 'partials/listbooks.html',
                	controller:'bookController'
                	
                })
                .otherwise({
                    redirectTo: '/'
                });

        }]);

    /*bind jQuery date-picker. Resource: Spyros A.*/
    angular.module('simpleApp')
        .directive('ngDate', function () { //ng-date will be used in html
            return {
                require : 'ngModel',
                link : function (scope, element, attrs, ngModelCtrl) {
                    $(function(){
                        element.datepicker({
                            inline:false,
                            dateFormat:'yy/mm/dd'
                        });
                    });
                }
            };
        });
}());