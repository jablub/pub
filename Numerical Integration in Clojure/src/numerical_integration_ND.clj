(ns numerical-integration-ND
    (:use clojure.test)
    (:use clojure.math.combinatorics)
    (:use numerical-integration-1D)
  )

(defn 
  ^{:test (fn[] 
            (assert (= (fn-2D [1 1])  10.0))
            )}     
fn-2D 
  "1D test function (x-2)^3 +10"
  [point-ND]
  (let [
        x (first point-ND)
        y (second point-ND)
        ]
    (+ 10 (Math/pow (- x 2) 3) y)
    )
  )

(fn-2D [1 1])


(defn 
  ^{:test (fn[] 
            (assert (= (fn-3D [1 1 1])  11.0))
            )}     
fn-3D 
  "1D test function (x-2)^3 +10"
  [point-ND]
  (let [
        x (first point-ND)
        y (second point-ND)
        z (nth point-ND 2)
        ]
    (+ 10 (Math/pow (- x 2) 3) y z)
    )
  )

(fn-3D [1 1 1])


(defn 
  ^{:test (fn[] 
            (assert (= (split-bounds-ND [[0 1]] [0.5])            '(((0 0.5)) ((0.5 1)))) )  
            (assert (= (split-bounds-ND [[0 1] [0 1]] [0.5 0.5])  '(((0 0.5) (0 0.5)) ((0 0.5) (0.5 1)) ((0.5 1) (0 0.5)) ((0.5 1) (0.5 1))) ))  
            )}  
split-bounds-ND
  "Split the nd bounds up. Will give a list of single bounds for 1D functions and a list of double bounds for 2D functions, etc. Used as the base when calculating area."
  [bounds step]
  (apply cartesian-product (map #(partition 2 1 (range-inclusive %1 %2)) bounds step))
  )


(split-bounds-ND [[0 2] [0 1]] [1 1]) 

(split-bounds-ND [[0 4] [0 4]] [1 1]) 



(defn 
  ^{:test (fn[] 
             (assert (= (trapezium-ND (fn [point] 2) [[0 2] [0 2]])   8))  
             (assert (= (trapezium-ND (fn [point] 2) [[0 2]])         4))  
             )}   
  trapezium-ND 
  "The n-volume of a trapezium is the base * the average of the heights"
  [f bounds]
  (let [points (apply cartesian-product bounds)
        average-of-heights (/ (reduce + (map f points)) (count points) )
        base (reduce * (map #(- (second %) (first %)) bounds)) ]
    (* base average-of-heights)
    )
  )

(trapezium-ND fn-2D [[0 1] [0 1]])


(defn 
  ^{:test (fn[] 
            (assert (= (volume-ND #(reduce + %) [[0 1]] [0.5])  0.5) )  
            (assert (= (volume-ND #(reduce + %) [[0 1] [0 1]] [0.5 0.5])  1.0) )  
            (assert (= (volume-ND #(reduce + %) [[0 1] [0 1] [0 1]] [0.5 0.5 0.5])  1.5) )  
            )}   
volume-ND
  "Sum the volume of all the trapeziums obtained by splitting the bounds up into their respective steps. Assumes f is always positive."
  [f bounds step]
  (reduce + (map #(trapezium-ND f %) (split-bounds-ND bounds step)))
  )

(run-tests)


;(volume-ND fn-2D [[0 1] [0 1]] [1 1])
;
;(volume-ND fn-2D [[0 4] [0 4]] [1 1])
;
;(volume-ND fn-3D [[0 1] [0 1] [0 1]] [1 1 1])
;
;(volume-ND fn-3D [[0 4] [0 4] [0 4]] [1 1 1])

