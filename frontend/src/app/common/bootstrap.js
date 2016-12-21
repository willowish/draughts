import angular from 'angular';
export default function(appModule) {
	angular.bootstrap(
		document, 
		[appModule.name]
	);
}