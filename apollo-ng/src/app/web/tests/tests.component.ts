import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import { TestDetail } from './test-detail/test-detail.component';

@Component({
  selector: 'app-tests',
  templateUrl: './tests.component.html',
  styleUrls: ['./tests.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class TestsComponent implements OnInit {

  constructor() { }

  testDetails: Array<TestDetail>;

  ngOnInit() {
        this.testDetails = [
            {
                name: 'Všestranný test míčových sportů',
                sections: [
                    {
                        title: 'Kdy zvolit',
                        content: 'Chci zjistit úroveň všestrannosti na míčové/týmové sporty'
                    },
                    {
                        title: 'Komu je určen',
                        content: 'Věk od 6 do 13 let'
                    },
                    {
                        title: 'Co získám',
                        content: 'Report s úrovní dovedností míčových/týmových sportů -> V jakém míčovém sportu jsem nejšikovnější'
                    },
                    {
                        title: 'Popis',
                        content: 'Kombinace míčových sportů zjišťuje úroveň všestrannosti -> Slalom s míčem a bez míčem, Střelba na koš/bránu, příhra na cíl, přesnost, dribling s míčem'
                    },
                    {
                        title: 'Postup/doporučení',
                        content: 'Po zjištění úrovně se doporučuje zvolit speciální test na konkrétní sport -> porovnání s jedinci (ostatními) v daném sportu\n'
                    }
                ]
            },
            {
                name: 'Všestranný test',
                sections: [
                    {
                        title: 'Kdy zvolit',
                        content: 'Chci zjistit úroveň základních předpokladů dovedností -> důležité'
                    },
                    {
                        title: 'Komu je určen',
                        content: 'Věk od 5'
                    },
                    {
                        title: 'Co získám',
                        content: 'Úroveň jednotlivých motorických dovedností'
                    },
                    {
                        title: 'Popis',
                        content: 'Jedná se o testy jako - reakce, koordinace, rychlost, výbušná síla -> letmý start, skoky, chůze po kladině'
                    },
                    {
                        title: 'Postup/doporučení',
                        content:  'Slouží jako vstupní test, který je základním předpokladem pro ostatní testy a doporučuje se ho zvolit jako první'
                    }
                ]
            },
            {
                name: 'Speciální test',
                sections: [
                    {
                        title: 'Kdy zvolit',
                        content: 'Dělám konkrétní sport a chci zjistit v čem bych se měl zlepšit a na čem pracovat nebo chci vyzkoušet více sportů a porovnat se s ostatními'
                    },
                    {
                        title: 'Komu je určen',
                        content: 'Věk od 10 let'
                    },
                    {
                        title: 'Co získám',
                        content: 'Úroveň dovedností v daném sportu'
                    },
                    {
                        title: 'Popis',
                        content: 'Jedná se o testy rychlosti s míčem a bez míče, analýza výskoku, ...'
                    },
                    {
                        title: 'Postup/doporučení',
                        content: 'S ohledem na výsledek se doporučuje test všestranný, který ve spojení se speciálním testem může odhalit slabé stránky'
                    }
                ]
            }
        ];
  }
}
