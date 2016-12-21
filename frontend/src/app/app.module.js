import angular from "angular";
import ngResource from 'angular-resource';
import resourcesFactory from './common/resourcesFactory'
export default angular.module('app', [
    resourcesFactory.name
])
