Mode <- function(x) {
  ux <- unique(x)
  ux[which.max(tabulate(match(x, ux)))]
}

# 1
myData <- read.csv('avianHabitat.csv')
myData <- myData$DBHt
myData<- myData[myData>0]

# 2
max_value <- max(myData)
min_value <- min(myData)
mean_value <- mean(myData)
median_value <- median(myData)
mode_value <- Mode(myData)
d_value <- var(myData)
sqrt_value <- sqrt(d_value)
quantile(myData)
? which.max
? tabulate
? quantile

# 3
b <- boxplot(myData)

# 4
myNeighbourData <- read.csv('avianHabitat.csv')
myNeighbourData <- myNeighbourData$WHt
myNeighbourData<- myNeighbourData[myNeighbourData>0]
b2 <- boxplot(myData, myNeighbourData, border = "brown", col = c("orange","red"))

# 5
myData1 <- sort(myData)
n <- length(myData)
v <- seq(from = 1/n, to = 1, by = 1/n)
plot(myData1, v)

# 6
hist(myData, breaks = 20, col = "lightblue")
plot(density(myData, bw = 0.2))

# 7
myData <- myData - mean_value
myData <- myData / sqrt_value
qqnorm(myData)
qqline(myData, col = "steelblue")
# точки не легки на одну линию => распределение данных не является нормальным