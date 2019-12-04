import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { BrowserModule } from '@angular/platform-browser';
import { TranslateModule } from '@ngx-translate/core';
import { VcSharedComponentsModule } from '../shared-components/vc-shared-components.module';
import { MessageService } from 'primeng/api';
import { ToastService } from './services/message.service';

@NgModule({
    imports: [
        CommonModule,
        BrowserModule,
        RouterModule,
        TranslateModule,
        VcSharedComponentsModule,
    ],
    providers: [
        MessageService,
        ToastService
    ],
    declarations: [],
    exports: []
})

export class CoreModule {
}
