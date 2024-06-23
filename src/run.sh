#!/bin/bash

spin() {
    local pid=$!
    local delay=0.1
    local spinstr='|/-\'
    sleep 0.5
    while [ "$(ps a | awk '{print $1}' | grep "$pid")" ]; do
        local temp=${spinstr#?}
        printf " [%c]  " "$spinstr"
        local spinstr=$temp${spinstr%"$temp"}
        sleep $delay
        printf "\b\b\b\b\b\b"
    done
    printf "    \b\b\b\b"
}

while true; do
    echo -n "Inserisci un numero maggiore di zero per ricavare i suoi divisori: "
    read numero

    re='^[0-9]+$'
    if [[ $numero =~ $re ]] && (( numero > 0 )); then
        javac Main.java
        (java Main "$numero") & spin
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