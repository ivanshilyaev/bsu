fileData <- read.table("result.txt")
plot(fileData$V1, fileData$V2, main = "x_0=0, a=75, c=3, m=1190438", xlab = "x_{i-1}", ylab = "x_{i}")
