var app = angular.module("myblogapp", ["ngSanitize"]);

app.controller("postcontoller", ($scope, $http) => {
  $http.get("/user/api/allpost").then(
    (response) => {
      $scope.posts = response.data;
      console.log($scope.posts);
    },
    (fail) => {
      console.log("failed");
    }
  );
});

app.controller("searchcontroller", ($scope, $http) => {
  $scope.hidebar = true;
  $scope.searchpost = () => {
    var keyword = $scope.searchkeyword;
    if (keyword == "") {
      $scope.hidebar = true;
    } else {
      $http.get("/user/api/searchpost/" + keyword).then(
        (response) => {
          if (response.data.length == 0) {
            $scope.hidebar = true;
          } else {
            $scope.hidebar = false;
            $scope.searchresult = response.data;
          }
          // console.log(response.data.length);
          // console.log(response);
        },
        (error) => {
          console.log(error);
        }
      );
    }
  };
});
