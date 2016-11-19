<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html ng-app="magicFilter">
<head>
    <title>Title</title>
    <link href="<c:url value="/resources/css/angular-material.min.css" />" rel="stylesheet" type="text/css">
    <link href="<c:url value="/resources/css/app.css" />" rel="stylesheet" type="text/css">

    <script src="<c:url value="/resources/js/lib/angular/angular.min.js" />"></script>
    <script src="<c:url value="/resources/js/lib/angular/angular-aria.min.js" />"></script>
    <script src="<c:url value="/resources/js/lib/angular/angular-animate.min.js" />"></script>
    <script src="<c:url value="/resources/js/lib/angular/angular-material.min.js" />"></script>
    <script src="<c:url value="/resources/js/lib/angular/ng-file-upload-all.js" />"></script>

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
                        <img flex-gt-xs="100" layout="column" ng-show="picFile[0] != null" ngf-src="picFile[0]"
                             class="thumb">
                        <div> 1. Оберіть необхідне зображення
                            <input type="file" id="file" class="input-text"
                                   name="attachement"
                                   accept="image/png, image/jpeg, image/jpg"
                                   ngf-select ng-model="picFile"
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
</body>
</html>
