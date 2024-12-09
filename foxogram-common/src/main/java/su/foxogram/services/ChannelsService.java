package su.foxogram.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import su.foxogram.constants.MemberConstants;
import su.foxogram.dtos.request.ChannelEditDTO;
import su.foxogram.dtos.response.MemberDTO;
import su.foxogram.exceptions.ChannelNotFoundException;
import su.foxogram.exceptions.MemberAlreadyInChannelException;
import su.foxogram.exceptions.MemberInChannelNotFoundException;
import su.foxogram.exceptions.MissingPermissionsException;
import su.foxogram.models.Channel;
import su.foxogram.models.Member;
import su.foxogram.models.User;
import su.foxogram.repositories.ChannelRepository;
import su.foxogram.repositories.MemberRepository;

import java.util.List;

@Slf4j
@Service
public class ChannelsService {
	private final ChannelRepository channelRepository;

	private final MemberRepository memberRepository;

	@Autowired
	public ChannelsService(ChannelRepository channelRepository, MemberRepository memberRepository) {
		this.channelRepository = channelRepository;
		this.memberRepository = memberRepository;
	}

	public Channel createChannel(User user, int type, String name) {
		String owner = user.getUsername();

		Channel channel = new Channel(0, name, type, owner);
		channelRepository.save(channel);

		Member member = new Member(user, channel, MemberConstants.Permissions.ADMIN.getBit());
		memberRepository.save(member);

		return channel;
	}

	public Channel getChannel(String name) throws ChannelNotFoundException {
		Channel channel = channelRepository.findByName(name);

		if (channel == null) throw new ChannelNotFoundException();

		return channel;
	}

	public Channel editChannel(Member member, Channel channel, ChannelEditDTO body) throws MissingPermissionsException {
		if (member.hasAnyPermission(MemberConstants.Permissions.ADMIN, MemberConstants.Permissions.MANAGE_CHANNEL))
			throw new MissingPermissionsException();

		if (body.getName() != null) channel.setName(body.getName());

		channelRepository.save(channel);

		return channel;
	}

	public void deleteChannel(Channel channel, User user) throws MissingPermissionsException {
		Member member = memberRepository.findByChannelAndUser(channel, user);

		if (member.hasPermission(MemberConstants.Permissions.ADMIN)) {
			channelRepository.delete(channel);
		} else {
			throw new MissingPermissionsException();
		}
	}

	public Member joinUser(Channel channel, User user) throws MemberAlreadyInChannelException {
		Member member = memberRepository.findByChannelAndUsername(channel, user.getUsername());

		if (member != null) throw new MemberAlreadyInChannelException();

		member = new Member(user, channel, 0);
		return memberRepository.save(member);
	}

	public void leaveUser(Channel channel, User user) throws MemberInChannelNotFoundException {
		Member member = memberRepository.findByChannelAndUser(channel, user);

		if (member == null) throw new MemberInChannelNotFoundException();

		member = memberRepository.findByChannelAndUser(channel, user);
		memberRepository.delete(member);
	}

	public List<MemberDTO> getMembers(Channel channel) {
		return memberRepository.findAllByChannel(channel).stream()
				.map(MemberDTO::new)
				.toList();
	}

	public Member getMember(Channel channel, String memberUsername) {
		return memberRepository.findByChannelAndUsername(channel, memberUsername);
	}
}
