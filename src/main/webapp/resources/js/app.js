(function () {
    'use strict';

    angular
        .module('magicFilter', [
            'ngAria',
            'ngAnimate',
            'ngMaterial',
            'ngFileUpload'
        ]).controller('AppController', ['Upload', function (Upload) {
        var vm = this;
        vm.init = init;
        vm.startUpload = startLoading;
        vm.isInvalidForm = isInvalidForm;

        function init() {
            vm.filters = getFilters();
            vm.selectedFilters = [];
            vm.picFile = {};
            vm.isLoading = false;
            vm.form = {};
        }

        function startLoading() {
            vm.isLoading = true;
        }

        function isInvalidForm() {
            return vm.isLoading || vm.form.$invalid;
        }

        function getFilters() {
            return [
                {id: '1', name: 'Фільтр 1'},
                {id: '2', name: 'Фільтр 2'},
                {id: '3', name: 'Фільтр 3'},
                {id: '4', name: 'Фільтр 4'},
                {id: '5', name: 'Фільтр 5'}
            ]
        }
    }]);
})();