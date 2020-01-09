import { Component, OnDestroy, OnInit } from '@angular/core';
import * as $ from 'jquery';
import 'jquery.easing';
import { TranslateService } from '@ngx-translate/core';
import { NavigationEnd, Router } from '@angular/router';
import { NgwWowService } from 'ngx-wow';
import { Subscription } from 'rxjs';
import { AuthService } from '../modules/auth/services/auth.service';
import { IUser } from '../modules/entities/user';

@Component({
    selector: 'app-web',
    templateUrl: './web.component.html',
    styleUrls: ['./web.component.scss']
})
export class WebComponent implements OnInit, OnDestroy {

    constructor(translate: TranslateService,
                private authService: AuthService,
                private router: Router,
                private wowService: NgwWowService) {

        this.router.events.subscribe(event => {
            // Reload WoW animations when done navigating to page,
            // but you are free to call it whenever/wherever you like
            if (event instanceof NavigationEnd) {
                this.wowService.init();
            }
        });
    }

    private wowSubscription: Subscription;

    title = 'Angular';


    ngOnInit(): void {

        this.authService.updateUser().subscribe((user: IUser) => {
            console.log(user);
        });

        $('#status').delay(100).fadeOut('slow');
        $('#preloader').delay(500).fadeOut('slow');
        $('body').delay(500).css({overflow: 'visible'});

        /* Scroll Up */
        $(window).scroll(function () {
            if ($(this).scrollTop() > 100) {
                $('.scroll-up').fadeIn();
            } else {
                $('.scroll-up').fadeOut();
            }
        });

        $('.scroll-up').click(function () {
            $('html, body').animate({
                scrollTop: 0
            }, 600);
            return false;
        });

        document.querySelectorAll('a[href^="#"]').forEach(anchor => {
            anchor.addEventListener('click', function (e) {
                e.preventDefault();

                const yOffset = -84;
                const id = this.getAttribute('href');
                const element = document.querySelector(id);
                if (element) {
                    const y = element.getBoundingClientRect().top + window.pageYOffset + yOffset;
                    window.scrollTo({top: y, behavior: 'smooth'});
                }
            });
        });

        this.wowSubscription = this.wowService.itemRevealed$.subscribe(
            (item: HTMLElement) => {
                // do whatever you want with revealed element
                console.log(item);
            });
    }

    ngOnDestroy() {
        // unsubscribe (if necessary) to WOW observable to prevent memory leaks
        this.wowSubscription.unsubscribe();
    }

}


