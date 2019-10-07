f <- function(x) {
  res <- x^3
  return(res)
}

swap <- function(a, b) {
  tmp <- a
  a <- b
  b <- tmp
}

count <- function(FUN = f, a, b, eps) {
  if (a == b)
    return(0)
  res <- 1
  if (a > b) {
    swap(a, b)
    res <- -1
  }
  s <- f(b) * (b - a)
  n <- 1
  repeat {
    n <- n * 2
    s2 <- s
    s <- 0.0
    c <- (b - a) / n
    for (i in 1:n) {
      s <- s + f(a + c * i) * c
    }
    if (abs(s-s2) < eps)
      break
  }
  res <- res * s
  return (res)
}

count(f, 0, 10, 0.001)
integrate(f, 0, 10)
