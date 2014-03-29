(ns numerical-integration-1D
  (:use clojure.test)
  )



(defn 
  ^{:test (fn[] 
            (assert (= (range-inclusive [0 1] 0.5))  '(0 0.5 1))
            )}   
range-inclusive
  "Similar to range but includes the end. Used to divide base up into segments to calculate volume"
  [bound step]
  (flatten (list (range (first bound) (second bound) step) (second bound)))
  )

(range-inclusive [0 4] 1)



(defn 
  ^{:test (fn[] 
            (assert (= (fn-1D 0)  2.0))
            (assert (= (fn-1D 4)  18.0))
            )}     
fn-1D 
  "1D test function (x-2)^3 +10"
  [x]
  (+ 10 (Math/pow (- x 2) 3))
  )

(fn-1D 4)



(defn 
  ^{:test (fn[] 
            (assert (= (split-bounds-1D [0 1] 0.5)            '((0 0.5) (0.5 1))    ) )  
            )}  
split-bounds-1D
  "Get a list of bounds split by step"
  [bound step]
  (partition 2 1 (range-inclusive bound step))
  )

(split-bounds-1D [0 4] 1)



(defn 
  ^{:test (fn[] 
             (assert (= (trapezium-1D fn-1D [3 4]) 14.5))  
             )}   
trapezium-1D 
  "The area of a trapezium is the base * the average of the heights"
  [f bound]
  (let [
        average-of-heights (/ (reduce + (map f bound)) 2 )
        base (- (second bound) (first bound)) 
        ]
    (* base average-of-heights)
    )
  )

(trapezium-1D fn-1D [0 1])



(defn 
  ^{:test (fn[] 
            (assert (= (volume-1D fn-1D [0 4] 1)  40.0) )  
            )}   
volume-1D
  "Sum the volume of all the trapeziums obtained by splitting the bounds up into their respective steps. Assumes f is always positive."
  [f bounds step]
  (reduce + (map #(trapezium-1D f %) (split-bounds-1D bounds step)))
  )


(run-tests)

(volume-1D fn-1D [0 4] 1) 
