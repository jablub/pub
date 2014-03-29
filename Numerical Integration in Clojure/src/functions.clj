(ns functions
  (:use clojure.math.combinatorics)
  (:use numerical-integration-1D)
  )

(first [0 4])

(second [0 4])

(range 0 4)

(count (range 0 4))

(partition 1 (range 0 4))

(partition 2 1 (range 0 4))

(map inc (range 0 4))

(reduce + (range 0 4))



(range-inclusive [0 4] 1)



(map #(* % %) (range 0 4))

(map #(+ %1 %2) (range 0 4)  (range 0 4))





(+ 0 1 2 3)

;(+ [0 1 2 3])

(apply + [0 1 2 3])

(cartesian-product [0 1])

(cartesian-product [0 1] [0 1])
