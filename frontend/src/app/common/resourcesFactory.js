import angular from 'angular';
import 'angular-resource';

let mod = angular.module('resourcesFactory', ['ngResource']);

mod.factory('boardResource', ['$resource', ($resource)=> {
    var board = $resource('/board', null, {
        get: {
            method: 'GET'
        },
        update: {
            method: 'PUT'
        }
    });
    return board;

}]);
export default mod
