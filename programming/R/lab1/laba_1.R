#1
i <- 25 # number in list
v1 <- seq(i, 6.5, length.out = 6)
base <- (abs(13-i) + 2)
q <- (100 / base) ^ (1 / 3)
v2 <- c(base, base * q, base * q^2, base * q^3)

#2
age <- c(18, 18, 18, 19)
hasDrivingLicense <- c(F, T, F, T)
hasDog <- factor(c("no", "no", "yes", "no"), levels = c("yes", "no"))
example <- data.frame(age, hasDrivingLicense, hasDog)
View(example)

#3
myData <- read.csv('iris.csv')
#a
nrow(myData[myData$Sepal.Width <= 3 & myData$Species == 'setosa', 1:6])
nrow(myData[myData$Sepal.Width <= 3 & myData$Species == 'versicolor', 1:6])
nrow(myData[myData$Sepal.Width <= 3 & myData$Species == 'virginica', 1:6])
#b
nrow(myData[myData$Petal.Length > 4.5 & myData$Species == 'setosa', 1:6])
nrow(myData[myData$Petal.Length > 4.5 & myData$Species == 'versicolor', 1:6])
nrow(myData[myData$Petal.Length > 4.5 & myData$Species == 'virginica', 1:6])
#c
nrow(myData[myData$Sepal.Width <= 3 & myData$Petal.Length > 4.5 & myData$Species == 'setosa', 1:6])
nrow(myData[myData$Sepal.Width <= 3 & myData$Petal.Length > 4.5 & myData$Species == 'versicolor', 1:6])
nrow(myData[myData$Sepal.Width <= 3 & myData$Petal.Length > 4.5 & myData$Species == 'virginica', 1:6])
#d
mean(myData[myData$Species == 'setosa',5])
mean(myData[myData$Species == 'versicolor',5])
mean(myData[myData$Species == 'virginica',5])
