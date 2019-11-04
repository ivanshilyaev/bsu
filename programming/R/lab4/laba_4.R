f <- function(x) {
  res <- x^3/5
  return(res)
}

#1
my_x = seq(1,10,0.1)
my_y = f(my_x)+rnorm(length(my_x),sd=15)
plot(my_x, my_y)

#2
# a - МНК
A <- matrix(c(length(my_x), sum(my_x), sum(my_x), sum(my_x^2)), nrow = 2, ncol = 2, byrow = TRUE)
B <- c(sum(my_y), sum(my_x * my_y))
solve(A, B)

# b - lm
?lm
model1 <- lm(formula = my_y ~ my_x)
print(model1)

#3
model2 <- lm(formula = my_y ~ f(my_x))
print(model2)

#4
?nls
model3 <- nls(formula = my_y ~ I(my_x^b2), start = list(b2 = 1), trace = TRUE)
print(model3)


#5
plot(my_x, my_y, xlim = c(0, 11), ylim = c(0, 300))
abline(model1)
lines(my_x, 2.9614 + 0.9547  * f(my_x), type = "l")
lines(my_x, I(my_x^2.24209), type = "l")


F1 <- function(x){
  return( -49.42 + 19.25*x)
}
F2 <- function(x){
  return(2.9614 + 0.9547  * f(x))
}
F3 <- function(x){
  return (x^2.24209)
}

#6
#newMy_x <- data.frame( my_x =  seq(10.1, 11, 0.1) )
points(11, F1(11))
points(11, F2(11))
points(11, F3(11))







