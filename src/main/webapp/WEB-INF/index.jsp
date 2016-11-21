<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html ng-app="magicFilter">
<head>
    <title>Magic filter</title>
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link href="<c:url value="/resources/css/angular-material.min.css" />" rel="stylesheet" type="text/css">
    <link href="<c:url value="/resources/css/app.css" />" rel="stylesheet" type="text/css">

    <script src="<c:url value="/resources/js/lib/Chart.bundle.min.js" />"></script>
    <script src="<c:url value="/resources/js/lib/Chart.min.js" />"></script>
    <script src="<c:url value="/resources/js/lib/angular/angular.min.js" />"></script>
    <script src="<c:url value="/resources/js/lib/angular/angular-aria.min.js" />"></script>
    <script src="<c:url value="/resources/js/lib/angular/angular-animate.min.js" />"></script>
    <script src="<c:url value="/resources/js/lib/angular/angular-material.min.js" />"></script>
    <script src="<c:url value="/resources/js/lib/angular/ng-file-upload-all.js" />"></script>
    <script src="<c:url value="/resources/js/lib/angular/angular-chart.min.js" />"></script>

    <script src="<c:url value="/resources/js/app.js" />"></script>
</head>
<body ng-cloak ng-controller="AppController as vm" ng-init="vm.init()">
<md-progress-linear ng-show="vm.isLoading" md-mode="indeterminate"></md-progress-linear>
<md-content class="md-padding" layout="row">
    <div flex-xs flex-gt-xs="100" layout="column">
        <md-card>
            <md-card-title>
                <md-card-title-text>
                    <span class="md-headline">Обробка зображення фільтрами</span>
                </md-card-title-text>
            </md-card-title>
            <md-card-content>
                <form novalidate name="vm.form" id="contactproForm" style="display: inline" method="post"
                      enctype="multipart/form-data">
                    <img flex-gt-xs="100" layout="column" ng-show="vm.picFile[0] != null" ngf-src="vm.picFile[0]"
                         class="thumb">
                    <div> 1. Оберіть необхідне зображення
                        <input type="file" id="file" class="input-text"
                               name="attachement"
                               accept="image/png, image/jpeg, image/jpg"
                               ngf-select ng-model="vm.picFile"
                               ng-disabled="vm.isLoading"
                               required="required"
                        />
                    </div>
                    <div>
                        2. Оберіть фільтри
                        <md-input-container>
                            <label>Список фільтрів</label>
                            <md-select ng-model="vm.selectedFilters" ng-disabled="vm.isLoading" required="required"
                                       multiple>
                                <md-option ng-value="filt.id" ng-repeat="filt in vm.filters">{{filt.name}}
                                </md-option>
                            </md-select>
                        </md-input-container>
                    </div>
                    <md-button class="md-raised md-primary" ng-disabled="vm.isInvalidForm()"
                               ng-click="vm.startUpload()">
                        Застосувати фільтр{{vm.selectedFilters.length > 1 ? 'и': ''}}
                    </md-button>
                </form>
            </md-card-content>
        </md-card>
    </div>
</md-content>


<div class='md-padding' layout="row" flex>
    <div layout="row" flex>
        <div class="parent" layout="column" ng-repeat="group in vm.response.groups" flex>
            <md-card class="card" ng-repeat="image in group">
                <img src="data:image/jpg;base64,{{image.base64Image}}">
                <i class="material-icons" ng-click="vm.download(image.base64Image)" title="Завантажити">get_app</i>
                <span class="filter-name">{{ image.filterName}}</span>
            </md-card>
        </div>
    </div>
</div>
</body>
</html>
