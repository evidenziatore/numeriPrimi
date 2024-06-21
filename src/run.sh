#!/bin/bash

while true; do
    echo -n "Inserisci un numero maggiore di zero per ricavare i suoi divisori: "
    read numero

    re='^[0-9]+$'
    if [[ $numero =~ $re ]] && (( numero > 0 )); then
        javac Main.java
        java -Xss16m Main "$numero"
    else
        echo "Input non valido. Inserisci un numero intero maggiore di zero."
        continue
    fi

    while true; do
        echo -n "Vuoi riprovare (s/n)? "
        read risposta

        if [[ $risposta == "s" ]]; then
            break
        elif [[ $risposta == "n" ]]; then
            exit
        else
            echo -n "Risposta non valida. "
        fi
    done
done