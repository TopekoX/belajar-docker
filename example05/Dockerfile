FROM alpine:3.21.3
WORKDIR /work
RUN addgroup -S ucup && adduser -S ucup -G ucup  \
	&& chown ucup /work \
	&& chmod -R g+rwx /work

COPY --chown=ucup:topekox *.sh /work
USER ucup
CMD ["/bin/sh" , "demoping.sh"]
