import { LOCALE_ID, NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {JwtInterceptor} from "./interceptors/jwt.interceptor";
import { LayoutComponent } from './layout/layout-component';
import { registerLocaleData } from '@angular/common';
import localePt from '@angular/common/locales/pt';
import {NgxMaskDirective, NgxMaskPipe, provideNgxMask} from "ngx-mask";

registerLocaleData(localePt);

@NgModule({
  declarations: [
    AppComponent,
    LayoutComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    NgxMaskDirective,
    NgxMaskPipe
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true },
    { provide: LOCALE_ID, useValue: 'pt-BR' },
    provideNgxMask(),
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
