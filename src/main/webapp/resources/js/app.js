(function () {
    'use strict';

    angular
        .module('magicFilter', [
            'ngAria',
            'ngAnimate',
            'ngMaterial',
            'ngFileUpload',
            'chart.js'
        ]).config(function (ChartJsProvider) {
        ChartJsProvider.setOptions({colors: ['#803690', '#00ADF9', '#DCDCDC', '#46BFBD', '#FDB45C', '#949FB1', '#4D5360']});
    }).controller('AppController', ['Upload', '$timeout', function (Upload, $timeout) {
        var vm = this;
        vm.init = init;
        vm.startUpload = startLoading;
        vm.isInvalidForm = isInvalidForm;
        vm.fileUpload = fileUpload;
        vm.download = download;

        function init() {
            vm.filters = getFilters();
            vm.selectedFilters = [];
            vm.picFile = {};
            vm.isLoading = false;
            vm.form = {};
            vm.series = [];
        }

        function startLoading() {
            vm.isLoading = true;
            if (vm.form.attachement.$valid && vm.picFile) {
                vm.fileUpload(vm.picFile);
            }
        }

        function download(base64Image) {
            window.location.href = 'data:application/octet-stream;base64,' + base64Image;
        }

        function fileUpload(file) {
            Upload.upload({
                url: '/upload-file',
                arrayKey: '',
                file: file[0],
                data: {filters: vm.selectedFilters}
            }).then(function (resp) {
                var images = resp['data'];

                vm.labels = ['КМКСВП','КМКСВП','КМКСВП'];
                vm.data = ['1','2','3'];

                vm.response = {
                    groups: partition(images, images.length / 3),
                    images: images
                };
                vm.isLoading = false;
            }, function (resp) {
                console.log('Error status: ' + resp.status);
                vm.isLoading = false;
            }, function (evt) {
                var progressPercentage = parseInt(100.0 * evt.loaded / evt.total);
                console.log('progress: ' + progressPercentage + '% ');
            });
        };

        function isInvalidForm() {
            return vm.isLoading || vm.form.$invalid;
        }

        function getFilters() {
            return [
                {id: 'Sobel-Filter', name: 'Sobel Filter'},
                {id: 'Kirsch_Filter', name: 'Kirsch Filter'}
            ]
        }

        function partition(input, size) {
            var newArr = [];
            for (var i = 0; i < input.length; i += size) {
                newArr.push(input.slice(i, i + size));
            }
            return newArr;
        }

        function getResponse() {
            return {
                statistics: {
                    labels: ["Eating", "Drinking", "Sleeping", "Designing", "Coding", "Cycling", "Running"],
                    data: [
                        [65, 59, 90, 81, 56, 55, 40],
                        [28, 48, 40, 19, 96, 27, 100]
                    ]
                }
            };
        }
    }]);
})();