package com.xzdz.common.net.base;

import androidx.annotation.IntRange;
import android.view.View;

import com.xzdz.common.net.exception.ExceptionHandle;

import org.reactivestreams.Subscription;

import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class RxScheduler {
	public static <T extends View> Disposable countDown(final T view, @IntRange(from = 1) final int second, final CountDownListener<T> listener) {
		if (listener == null || second <= 0) return null;
		return Flowable.intervalRange(0, second + 1, 0, 1, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread())
				.doOnNext(new Consumer<Long>() {
					@Override
					public void accept(Long aLong) throws Exception {
						listener.onCountDownProgress(view, (int) (second - aLong));
					}
				}).doOnComplete(new Action() {
					@Override
					public void run() throws Exception {
						listener.onCountDownComplete(view);
					}
				}).doOnSubscribe(new Consumer<Subscription>() {
					@Override
					public void accept(Subscription subscription) throws Exception {
						listener.onBindCountDown(view);
					}
				}).subscribe();

	}
	public static class IO_MAIN<T> implements ObservableTransformer<T, T> {
		@Override
		public ObservableSource<T> apply(Observable<T> upstream) {
			return upstream.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
		}
	}

	public static class IO<T> implements ObservableTransformer<T, T> {
		@Override
		public ObservableSource<T> apply(Observable<T> upstream) {
			return upstream.subscribeOn(Schedulers.io());
		}
	}
	public static class Main<T> implements ObservableTransformer<T, T> {
		@Override
		public ObservableSource<T> apply(Observable<T> upstream) {
			return upstream.observeOn(AndroidSchedulers.mainThread());
		}
	}

	public static <T> Function<Throwable, ObservableSource<T>> handlerException() {
		return new Function<Throwable, ObservableSource<T>>() {
			@Override
			public ObservableSource<T> apply(Throwable throwable) throws Exception {
				return Observable.error(ExceptionHandle.handleException(throwable));
			}
		};
	}


	public static class HandlerException<T> implements Function<Throwable, Observable<T>> {
		@Override
		public Observable<T> apply(Throwable throwable) throws Exception {
		    throwable.printStackTrace();
			ExceptionHandle.ResponseThrowable responeThrowable = ExceptionHandle.handleException(throwable);
			return Observable.error(responeThrowable);
		}
	}

	private static class ViewObservable implements ObservableOnSubscribe<View> {
		private View view;

		ViewObservable(View view) {
			this.view = view;
		}

		@Override
		public void subscribe(final ObservableEmitter<View> emitter) throws Exception {
			View.OnClickListener listener = new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if (!emitter.isDisposed()) {
						emitter.onNext(view);
					}
				}
			};
			view.setOnClickListener(listener);
		}
	}

	public interface CountDownListener<T extends View> {
		void onBindCountDown(T view);
		void onCountDownProgress(T view, int second);
		void onCountDownComplete(T view);
	}

}