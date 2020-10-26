var app = angular.module("myblogapp", ['ngSanitize']);

app.controller("postcontoller", ($scope, $http) => {

  $http.get("http://localhost/user/api/allpost").then(
    (response) => {
      $scope.posts = response.data;
      console.log($scope.posts);
    },
    (fail) => {
      console.log("failed");
    }
  );
});