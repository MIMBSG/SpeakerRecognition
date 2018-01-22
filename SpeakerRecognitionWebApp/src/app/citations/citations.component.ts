import { Component, OnInit } from '@angular/core';
import {Citations} from '../citations';

@Component({
  selector: 'app-citations',
  templateUrl: './citations.component.html',
  styleUrls: ['./citations.component.css']
})
export class CitationsComponent implements OnInit {

  public citation: Citations = {
    id: 0,
    text: '',
    author: ''
  };

  constructor() {
    const citationsList: Citations[] = [
      { id: 1, text: 'Nauka jest jak niezmierne morze. Im więcej jej pijesz, tym bardziej jesteś spragniony.', author: 'Stefan Żeromski'},
      { id: 2, text: 'Co my wiemy, to tylko kropelka. Czego nie wiemy, to cały ocean.', author: 'Issac Newton'},
      { id: 3, text: 'Duża część postępu w nauce była możliwa dzięki ludziom niezależnym lub myślącym nieco inaczej.', author: 'Chris Darimont'},
      { id: 4, text: 'Wszystko, czego się dotąd nauczyłeś, zatraci sens, jeśli nie potrafisz znaleźć zastosowania dla tej wiedzy.', author: ' Paulo Coelho'},
      { id: 5, text: 'Żyj tak, jakbyś miał umrzeć jutro. Ucz się tak, jakbyś miał żyć wiecznie.', author: 'Gandhi Mahatma'},
      { id: 6, text: 'Powiedz mi, to zapomnę. Naucz mnie, to może zapamiętam. Zaangażuj mnie, to się nauczę.', author: 'Benjamin Franklin'},
      { id: 7, text: 'Ucz się tak, jakbyś niczego jeszcze nie osiągnął, i lękaj się, byś nie stracił tego, co już osiągnąłeś.', author: 'Konfucjusz'},
      { id: 8, text: 'Choćbyśmy nawet mogli stać się uczonymi uczonością drugich, mądrzy możemy być jedynie własną mądrością.', author: 'Michel de Montaigne'},
      { id: 9, text: 'Zawsze idź przez życie tak jakbyś miał coś nowego do nauczenia się, a tak się stanie.', author: 'Vernon Howard'},
      { id: 10, text: 'Stworzenie czegoś nowego, a jednocześnie zgodnego z tym, co wiadomo dotychczas jest niezwykle trudne.', author: 'Richard Feynman'},
      { id: 11, text: 'Chcę wiedzieć dlaczego istnieje wszechświat, dlaczego jest coś więcej niż nic.', author: 'Stephen Hawking'},
      { id: 12, text: 'Fundamentalna zasada rządząca wszechświatem: przyczyny występują przed skutkami, nigdy odwrotnie.', author: 'Stephen Hawking'},
      { id: 13, text: 'Znane są tysiące sposobów zabijania czasu, ale nikt nie wie, jak go wskrzesić.', author: 'Albert Einstein'},
      { id: 14, text: 'Wyobraźnia bez wiedzy może stworzyć rzeczy piękne. Wiedza bez wyobraźni najwyżej doskonałe.', author: 'Albert Einstein'},
      { id: 15, text: 'Nie wyrażaj małej rzeczy w wielu słowach, lecz rzecz wielką w niewielu.', author: 'Pitagoras'},
      { id: 16, text: 'Ideałem możemy się tylko stawać, by mimo to nigdy się nim nie stać.', author: 'Pitagoras'},
      { id: 17, text: 'Dopiero przy końcu roboty można poznać, od czego trzeba było zacząć.', author: 'Blaise Pascal'},
    ];
    let random: number = Math.floor((Math.random() * (citationsList.length-1)) + 1);
    this.citation = citationsList[random];
  }

  ngOnInit() {
  }


}
