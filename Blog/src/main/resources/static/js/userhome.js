var app = angular.module("myblogapp", ["ngSanitize"]);

app.controller("postcontoller", ($scope, $http) => {
  $http.get("/user/api/allpost").then(
    (response) => {
      $scope.posts = response.data;
      //console.log($scope.posts);
    },
    (fail) => {
      console.log("failed");
    }
  );
});

app.controller("showalltags",($scope,$http)=>{
    	$http.get("/user/api/alltags").then(
    	(response)=>{
    		$scope.alltags=response.data;
    		console.log(response.data);
    	}
    	,
    	(error)=>{
    		console.log(error);
    	}		
    	)
    });
    
app.controller("search",($scope,$http)=>{
    	$scope.searchfun=(keyword)=>{
    	window.open("https://www.google.com/search?q="+keyword,'_blank')
    	
    	}
    });

app.controller("switchtofollow",($scope,$http)=>{
	$scope.switch=false;
	$http.get("/user/api/allpostbyfollowed").then(
    (response) => {
      $scope.postsoffollowed = response.data;
      if(response.data.length==0)
      {
      $scope.showmessage=true;
      console.log("no post");
      }
    },
    (fail) => {
      console.log("failed");
    }
  );
});

app.controller("searchcontroller", ($scope, $http) => {
  $scope.hidebar = true;
  $scope.hidepost = true;
  $scope.hideuser = true;
  $scope.searchpost = () => {
    var keyword = $scope.searchkeyword;
    if (keyword == "") {
      $scope.hidebar = true;
      $scope.hidepost = true;
    } else {
      $http
        .get("/user/api/searchpost/" + keyword)
        .then(
          (response) => {
            console.log("post: " + response.data.length);
            if (response.data.length == 0) {
              $scope.hidebar = true;
              $scope.hidepost = true;
            } else {
              $scope.hidepost = false;
              $scope.hidebar = false;
              $scope.searchresult = response.data;
            }
            // console.log(response.data.length);
            // console.log(response);
          },
          (error) => {
            console.log(error);
          }
        )
        .then(
          $http.get("/user/api/searchuser/" + keyword).then(
            (response) => {
              console.log("user: " + response.data.length);
              if (response.data.length == 0) {
                $scope.hidebar = true;
                $scope.hideuser = true;
              } else {
                $scope.hideuser = false;
                $scope.hidebar = false;
                $scope.searchresultt = response.data;
                console.log(response.data);
              }
            },
            (error) => {
              console.log(error);
            }
          )
        );
    }
  };
});
