library(cluster)
library(fpc)
library(party)

x<-c(runif(30, 0, 10), runif(30, 10, 20),runif(30, 20, 30))
y<-c(runif(30, 0, 10),runif(30, 5, 15),runif(30, 10, 20))

df<-data.frame("x"=x, "y"=y)
plot(x,y)

xy_clast<-kmeans(df, 3)
xy_clast
plot(x,y,col=xy_clast$cluster,main="K-MEANS")

xy_clara<-clara(df, 4, metric = "euclidean", stand = FALSE, samples = 30)
plot(x,y,col=xy_clara$clustering,main="CLARA")

xy_pam<-pam(df, 4, metric = "euclidean")
plot(x, y, col=xy_pam$clustering, main="PAM")

xy_kmeansruns<-kmeansruns(df)
plot(x, y, col=xy_kmeansruns$cluster, main="KMEANRUNS")

xy_pamk<-pamk(df)
xy_pamk
plot(x, y, col=xy_pamk$pamobject$clustering, main="PAMK")

df1<-data.frame("x"=x, "y"=y, "class"=xy_kmeansruns$cluster)
xy_ctree<-ctree(class~.,df1)
xy_ctree
plot(xy_ctree)
