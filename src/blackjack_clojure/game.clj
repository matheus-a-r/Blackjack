(ns blackjack-clojure.game
  (:require [card-ascii-art.core :as card]))


(defn new-card []
  "Generates a card number between 1 and 13"
  (inc (rand-int 13)))

(defn a->11 [card]
  (if (= card 1) 11 card))

(defn jqk->10 [card]
  (if (> card 10) 10 card))

(defn points-cards [cards]
      (let [cards-without-jqk (map jqk->10 cards)
            cards-with-a11 (map a->11 cards-without-jqk)
            points-with-a-1 (reduce + cards-without-jqk)
            points-with-a-11 (reduce + cards-with-a11)]
        (if (> points-with-a-11 21) points-with-a-1 points-with-a-11)))

(defn player [player-name]
  (let [card1 (new-card)
        card2 (new-card)
        cards [card1 card2]
        points (points-cards cards)]
    {:player-name player-name
     :cards [card1 card2]
     :points points}))

;conj == append
;assoc recebe um mapa, chave e valor / associa o valor a chave
;update recebe um mapa, chave e uma função
(defn more-card [player]
  (let [card (new-card)
        cards (conj (:cards player) card)
        new-player (update player :cards conj card)
        points (points-cards cards)]
    (assoc new-player :points points)))

(defn game [player]
  (println (:player-name player)": mais cartas? ")
  (if (= (read-line) "sim")
    (let [player-with-more-cards (more-card player)]
      (card/print-player player-with-more-cards)
      (game player-with-more-cards))
    player))

(def player-1 (player "Matheus Alves"))
(card/print-player player-1)

(def dealer (player "Dealer"))
(card/print-player dealer)

(game player-1)